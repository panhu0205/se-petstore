package com.services.api.form.order;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel
@Data
public class ValidateForm {
    
    private Long orderId;
}
