package com.mitocode.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class FilterConsultDTO {

  @Pattern(regexp = "[0-9]+")
  private String dui;
  private String fullname;
}