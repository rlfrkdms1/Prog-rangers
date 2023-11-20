package com.prograngers.backend.exception;

public enum ErrorCode {
    INVALID_REQUEST_BODY("검증을 통과하지 못한 요청."),
    SOLUTION_NOT_FOUND("풀이를 찾을 수 없습니다."),
    PROBLEM_NOT_FOUND("문제를 찾을 수 없습니다."),
    PROBLEM_LINK_NOT_FOUND("문제 링크가 유효하지 않습니다."),
    COMMENT_NOT_FOUND("댓글을 찾을 수 없습니다."),
    REVIEW_NOT_FOUND("리뷰를 찾을 수 없습니다."),
    ALGORITHM_NOT_EXISTS("알고리즘 타입을 확인해 주세요."),
    DATASTRUCTURE_NOT_EXISTS("자료구조 타입을 확인해 주세요."),
    LEVEL_NOT_EXISTS("레벨 타입을 확인해 주세요."),
    SORT_TYPE_NOT_EXISTS("정렬 타입을 확인해 주세요."),
    MEMBER_NOT_FOUND("회원을 찾을 수 없습니다."),
    EXPIRED_TOKEN("토큰의 유효기간이 만료되었습니다."),
    FAIL_TO_ENCODED("비밀번호를 암호화하는데 실패했습니다."),
    FAIL_TO_DECODED("비밀번호를 복호화하는데 실패했습니다."),
    NOT_FOUND_REFRESH_TOKEN("유효한 refresh token이 존재하지 않습니다."),
    NOT_EXIST_REFRESH_TOKEN("REFRESH 토큰이 존재하지 않습니다."),
    LANGUAGE_NOT_EXISTS("언어 타입을 확인해 주세요."),
    INCORRECT_PASSWORD("비밀번호가 일치하지 않습니다."),
    NOT_EXIST_ACCESS_TOKEN("ACCESS TOKEN이 존재하지 않습니다."),
    ALREADY_EXIST_MEMBER("이미 존재하는 회원입니다."),
    ALREADY_EXIST_NICKNAME("이미 존재하는 닉네임입니다."),
    INCORRECT_NAVER_CODE("state가 일치하지 않습니다."),
    INVALID_ACCESS_TOKEN("유효하지 않은 토큰 값입니다."),
    MISSING_ISSUER_TOKEN("Issuer가 존재하지 않는 토큰입니다."),
    NOT_PROGRANGERS_TOKEN("발급자가 잘못된 토큰입니다."),
    UNSUPPORTED_TOKEN("지원하지 않는 토큰 형식입니다."),
    INCORRECTLY_CONSTRUCTED_TOKEN("잘못된 구조의 토큰입니다."),
    FAILED_SIGNATURE_TOKEN("서명에 실패한 토큰입니다."),
    UNAUTHORIZED_MEMBER("인가되지 않은 회원입니다."),
    TYPE_MISMATCH("쿼리 스트링 타입을 확인해주세요"),
    BLANK_NICKNAME("닉네임은 빈칸일 수 없습니다."),
    COMMENT_ALREADY_DELETED("이미 삭제된 댓글입니다."),
    REVIEW_ALREADY_DELETED("이미 삭제된 리뷰입니다."),
    ALREADY_EXISTS_LIKE("이미 좋아요를 누른 풀이입니다."),
    NOT_FOUND_LIKE("좋아요를 찾을 수 없습니다."),
    INVALID_CLAIM_TYPE("토큰의 claim 값은 Long 타입이어야 합니다."),
    INVALID_NOTIFICATION_TYPE("잘못된 알림 유형입니다."),
    SSE_DISCONNECTED("SSE 연결 오류입니다."),
    PRIVATE_SOLUTION("비공개 풀이를 열람할 수 없습니다."),
    INVALID_PAGE_NUMBER("잘못된 페이지 번호입니다."),
    ALREADY_FOLLOWING("이미 팔로우하고 있는 회원입니다."),
    NOT_FOUND_FOLLOW("팔로우 하지 않은 회원입니다."),
    NOT_EXIST_OLD_PASSWORD("비밀번호 변경시, 기존 비밀번호 또한 입력해야 합니다."),
    INVALID_PARENT("부모가 존재하지 않습니다."),
    DIFFERENT_SOLUTION("생성하려는 자식과 부모의 푸링가 다릅니다."),
    DIFFERENT_CODE_LINE_NUMBER("존재하지 않는 코드 줄번호 입니다."),
    DELETED_MEMBER("탈퇴한 사용자입니다.");

    private String errorMessage;

    private ErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

}
