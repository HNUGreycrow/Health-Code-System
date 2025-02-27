package org.software.code.common.result;

import lombok.Data;

@Data
/**
 * 通用的结果返回类，用于封装接口返回的数据、状态码和消息。
 * 该类使用泛型 T 来表示返回的数据类型，使得它可以灵活地应用于不同类型的数据返回。
 * 通过提供一系列静态方法，可以方便地创建成功或失败的结果对象。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 *
 * @param <T> 结果中包含的数据类型
 */
public class Result<T> {

    /**
     * 接口返回的状态码，用于表示请求的处理结果状态，例如 200 表示成功，500 表示失败等。
     */
    private Integer code;

    /**
     * 接口返回的消息，用于对处理结果进行描述性说明，例如成功时显示 "操作成功"，失败时显示具体的错误信息。
     */
    private String message;

    /**
     * 接口返回的数据，其类型由泛型 T 决定，可以是任意类型的数据，如实体对象、列表等。
     */
    private T data;

    /**
     * 创建一个表示成功的结果对象，包含具体的数据。
     * 状态码和消息将使用 ResultEnum.SUCCESS 枚举中的默认值。
     *
     * @param data 要返回的数据
     * @param <T>  数据的类型
     * @return 表示成功的结果对象
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 创建一个表示成功的结果对象，允许自定义消息并包含具体的数据。
     * 状态码将使用 ResultEnum.SUCCESS 枚举中的默认值。
     *
     * @param message 自定义的成功消息
     * @param data    要返回的数据
     * @param <T>     数据的类型
     * @return 表示成功的结果对象
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), message, data);
    }

    /**
     * 创建一个表示失败的结果对象，不包含具体的数据。
     * 状态码和消息将使用 ResultEnum.FAILED 枚举中的默认值。
     *
     * @return 表示失败的结果对象
     */
    public static Result<?> failed() {
        return new Result<>(ResultEnum.FAILED.getCode(), ResultEnum.FAILED.getMessage(), null);
    }

    /**
     * 创建一个表示成功的结果对象，不包含具体的数据。
     * 状态码和消息将使用 ResultEnum.SUCCESS 枚举中的默认值。
     *
     * @return 表示成功的结果对象
     */
    public static Result<?> success() {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 创建一个表示失败的结果对象，允许自定义失败消息，不包含具体的数据。
     * 状态码将使用 ResultEnum.FAILED 枚举中的默认值。
     *
     * @param message 自定义的失败消息
     * @return 表示失败的结果对象
     */
    public static Result<?> failed(String message) {
        return new Result<>(ResultEnum.FAILED.getCode(), message, null);
    }

    /**
     * 创建一个表示失败的结果对象，根据传入的 IResult 接口实现类提供状态码和消息，不包含具体的数据。
     *
     * @param errorResult 实现了 IResult 接口的对象，用于提供失败的状态码和消息
     * @return 表示失败的结果对象
     */
    public static Result<?> failed(IResult errorResult) {
        return new Result<>(errorResult.getCode(), errorResult.getMessage(), null);
    }

    /**
     * 无参构造函数，用于创建一个空的结果对象，通常在需要手动设置属性时使用。
     */
    public Result() {
    }

    /**
     * 有参构造函数，用于创建一个包含状态码、消息和数据的结果对象。
     *
     * @param code    状态码
     * @param message 消息
     * @param data    数据
     */
    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 创建一个自定义的结果对象，允许手动设置状态码、消息和数据。
     *
     * @param code    状态码
     * @param message 消息
     * @param data    数据
     * @param <T>     数据的类型
     * @return 自定义的结果对象
     */
    public static <T> Result<T> instance(Integer code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
}

