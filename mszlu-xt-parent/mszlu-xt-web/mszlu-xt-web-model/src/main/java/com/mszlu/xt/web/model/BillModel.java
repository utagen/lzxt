package com.mszlu.xt.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillModel {

    private Long id;

    private String name;

    private String billDesc;

    private String billType;

}