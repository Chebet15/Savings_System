package co.ke.tech.Savings_System.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiResponse<T> {
    private String message;
    private T entity; //{}
    private Integer statusCode; //201
}