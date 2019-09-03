package top.ok.fine.pomegranate.system.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @program
 * @description:
 * @author: zhengLin
 * @date 2019/08/28
 */

@Data
public class JsonResponse<T> {

    @JsonProperty("code")
    private int code;

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("data")
    private T data;


    @Data
    public static class Token {
        @JsonProperty("token")
        private String token;
    }

}
