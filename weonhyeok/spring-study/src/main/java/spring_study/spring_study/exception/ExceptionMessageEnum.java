package spring_study.spring_study.exception;

public enum ExceptionMessageEnum {
    NO_DATA_EXCEPTION("질문글을 찾을 수 없거나 질문글이 존재하지 않습니다.");

    private final String errorMessage;

    ExceptionMessageEnum(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
}
