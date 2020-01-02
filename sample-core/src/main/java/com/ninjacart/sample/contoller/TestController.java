package com.ninjacart.sample.contoller;

import com.ninjacart.sample.model.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test/")
public class TestController {

  @GetMapping(value = "publish")
  public ApiResponse<String> publish(@RequestBody String message){
      return new ApiResponse<>(message);
  }


}
