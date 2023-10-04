package com.linktic.challengelinktic.util;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linktic.challengelinktic.business.dto.ProductDto;
import com.linktic.challengelinktic.business.dto.ResponseDto;

@SpringBootTest
class ResponseUtilTest {

   @Mock
   private ObjectMapper objectMapper;

   @Test
   void responseTest() {
      final ResponseDto responseTest = ResponseUtil.response(HttpStatus.OK, null);
      assertNotNull(responseTest);
      assertEquals(HttpStatus.OK.name(), responseTest.getMessage());
      assertNotNull(responseTest.getTime());
      assertNull(responseTest.getData());
   }

   @Test
   @DisplayName("Junit test convert object to json string")
   void objectToJsonStringOk() throws JsonProcessingException {

      final ProductDto jsonTest = ProductDto
            .builder()
            .id(NumberUtils.LONG_ONE)
            .description("json description")
            .name("json name")
            .price(NumberUtils.createBigDecimal("53.03"))
            .build();
      final String expectedResult = "{\"id\":1,\"name\":\"json name\",\"description\":\"json description\",\"price\":53.03}";
      when(objectMapper.writeValueAsString(jsonTest)).thenReturn(expectedResult);
      final String result = ResponseUtil.objectToJsonString(jsonTest);
      assertNotNull(result);
      assertThat(result).isNotEmpty();
      assertEquals(expectedResult, result);
   }

   @Test
   @DisplayName("Junit test convert object to json string")
   void objectToJsonStringError() throws JsonProcessingException {
      final LocalDateTime nowTest = LocalDateTime.now();
      when(objectMapper.writeValueAsString(LocalDateTime.now())).thenThrow(JsonProcessingException.class);
      final String result = ResponseUtil.objectToJsonString(nowTest);
      assertEquals(StringUtils.EMPTY, result);
   }
}