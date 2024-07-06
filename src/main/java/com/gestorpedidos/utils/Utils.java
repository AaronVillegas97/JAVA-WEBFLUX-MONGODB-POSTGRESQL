package com.gestorpedidos.utils;

import com.gestorpedidos.dto.PedidoDto;
import com.gestorpedidos.entity.PedidoEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

public class Utils {
    public static PedidoDto entityToDto(PedidoEntity pedidoEntity) {
        PedidoDto pedidoDto = new PedidoDto();
        BeanUtils.copyProperties(pedidoEntity, pedidoDto);
        return pedidoDto;
    }

    public static PedidoEntity dtoToEntity(PedidoDto pedidoDto) {
        PedidoEntity pedidoEntity = new PedidoEntity();
        BeanUtils.copyProperties(pedidoDto, pedidoEntity);
        return pedidoEntity;
    }
//    public static <T> ResponseEntity<?> buildResponse(T data, String message, HttpStatus status) {
//        return ResponseEntity.status(status).body(new CustomResponse<>(data, message, status.value()));
//    }
//
//    public static class CustomResponse<T> {
//        private String message;
//        private T data;
//        private int status;
//
//        public CustomResponse(T data, String message, int status) {
//            this.data = data;
//            this.message = message;
//            this.status = status;
//        }
//
//        // Getters y setters
//        public T getData() {
//            return data;
//        }
//
//        public void setData(T data) {
//            this.data = data;
//        }
//
//        public String getMessage() {
//            return message;
//        }
//
//        public void setMessage(String message) {
//            this.message = message;
//        }
//
//        public int getStatus() {
//            return status;
//        }
//
//        public void setStatus(int status) {
//            this.status = status;
//        }
//    }
}
