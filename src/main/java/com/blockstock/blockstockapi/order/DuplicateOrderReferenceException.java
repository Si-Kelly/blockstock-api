package com.blockstock.blockstockapi.order;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "An order with this reference already exists")
public class DuplicateOrderReferenceException extends RuntimeException {

}
