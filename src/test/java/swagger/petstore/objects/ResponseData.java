package swagger.petstore.objects;

public class ResponseData {

    private Integer code;
    private String type;
    private String message;

    public ResponseData(Integer code, String type, String message) {
        this.code = code;
        this.type = type;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
