## 1. 개요

1. 카드 결제 프로세스
    
    **`승인 → 매입 → 정산`**
    
    - 승인 : 가맹점에서 고객에게 카드 결제를 받는 행위, 채권 채무 관계를 발생시키는 신용 행위
    - 매입 : 카드사는 가맹점의 채권을 매입
    - 대금 : 카드사는 가맹점의 카드 수수료를 제외한 나머지 금액을 정산

## 2. 용어정리

- **VAN(Value Added Network) 사업자**
    - 카드사와 가맹점 사이의 통신을 중계하고 부가 서비스를 제공하는 역할
- **승인**
    - **직승인**
        - 가맹점이 특정 카드사와 직접 연동
    - **VAN승인**
        - VAN사가 여러 카드사를 대행
    - **망취소**
        - 승인 요청을 보냈는데 VAN사나 카드사 쪽에서 응답이 없거나 타임아웃이 발생했을 때, 해당 거래 요청을 취소(무효화)하기 위해 즉시 취소 전문을 보냄

## 2. 결제서비스 기준 결제 흐름

### 가맹점 결제 프로세스

**`POS →  VAN사 → 카드사 → VAN사 → POS`**

1. **POS → VAN사 결제 요청**
    - POS에서 카드번호, 유효기간, CVC, 결제금액 등 거래 정보를 [ISO 8583](https://ko.wikipedia.org/wiki/ISO_8583) 메시지로 생성
    - VAN사에 승인 요청 메시지 전송
2. **VAN사 → 카드사 요청 전단**
    - 해당 카드사 승인 서버로 라우팅
3. **카드사 승인 처리**
    - **카드 유효성 검증**
    - 승인 또는 거절 응답 생성 후 VAN사로 회신
4. **VAN사 → POS 카드 결제 응답 전송**
    - 카드사 응답 메시지를 POS 처리용 ISO 8583 포맷으로 재변환
    - POS에 승인 성공/실패 결과 및 응답 코드 전송
5. **POS 응답 수신 및 후속 처리**
    - 승인 성공: 영수증 출력, 재고 차감, 고객 안내
    - 승인 실패: 오류 메시지 표시

### 승인된 결제건 청구 및 정산 프로세스

**`POS → VAN사 → 카드사 → VAN사 → 가맹점`**

1. **청구 요청**
    1. 승인 완료된 거래 내역을 VAN사에 전송
    2. VAN사는 수집된 승인정보를 카드에 일괄 매입 요청
2. **카드사 매입 처리**
    1. 카드사는 VAN사로부터 받은 승인 내역을 매입 승인 또는 거절 응답을 생성
    2. VAN사는 카드사 응답을 수신한 뒤 가맹점에 매입 처리 결과를 전달
3. **대사**
    1. 카드사는 매입기관으로부터 승인·매입 정보를 받아 내부 시스템에 기록
    2. VAN사는 가맹점이 보유한 매출 데이터와 카드사 대사 데이터를 대조
        - 거래 건수, 금액, 승인번호 등이 일치하는지 확인
        - 누락·불일치 건은 별도 리포팅 및 재처리
4. **정산**
    1. 카드사(또는 매입사)가 VAN사에 최종 정산금액을 확정 통보
        - 가맹점 수수료(카드사 수수료 + VAN 수수료) 공제
    2. VAN사는 가맹점 계좌로 순수 입금액을 이체

## 결제서비스 요구사항

### 1. 결제

1. **요청 수집**
    - 필수 정보: 카드번호(PAN), 유효기간, CVC, 결제금액, 거래 ID(transactionId)
    - 입력 검증: 필수 필드, 금액 범위, 중복 transactionId 방지
2. **메시지 변환 및 전송**
    - 요청 → [ISO 8583](https://ko.wikipedia.org/wiki/ISO_8583) 포맷 변환
        - 카드번호 마스킹(PAN 전체 마스킹, 뒤 4자리만 저장)
    - VAN사 승인 서버에 TLS 채널로 요청
3. **응답 처리**
    - **승인(00) 성공**
        - 거래 상태 → `AUTHORIZED`
        - 승인번호, 승인일시 기록
    - **거절/오류**
        - 오류 코드·메시지 저장
        - 클라이언트에 실패 응답
4. **에러·재시도 로직**
    - 네트워크 장애, 타임아웃 등 예외 발생 시 자동 재시도
    - 응답 미수신 시 망취소 처리
    - idempotency 보장: 같은 transactionId 중복 처리 방지

### 2. 청구·매입 처리

1. **승인 거래 조회**
    - 승인 상태인 거래 목록 조회
2. **청구 요청 생성 및 전송**
    - 거래 리스트 → ISO 8583 Capture 메시지 또는 VAN사 지정 포맷(CSV/XML)으로 변환
    - FTP/SFTP 또는 REST API 호출로 VAN사 매입 시스템 전송
3. **매입 응답 수신 및 처리**
    - **매입 승인**
        - 매입 승인 처리
    - **매입 거절**
        - 매입 거절 처리

### 3. 대사 처리

1. **대사 파일 수신**
    - 익영업일 새벽 SFTP/FTP 또는 API로 수신
2. **거래 매칭 로직**
    - 파일 내 거래 건수·금액·승인번호 기준으로 내부 청구·매입 기록과 매칭
    - 결과 분류
        - **일치**
        - **불일치/누락**
            - 상세 사유(금액 불일치, 미매칭, 추가 건 등) 기록
