package com.roi.roisupplying.supplyorder.dao;

import com.roi.roisupplying.supplyorder.SupplyOrderDTO;

public class SupplyOrderMapper {
    public static SupplyOrder toEntity(SupplyOrderDTO dto) {
        SupplyOrder entity = new SupplyOrder();
        entity.setClientNumber(dto.getClientNumber());
        entity.setClosingDay(dto.getClosingDay());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setServicePointIdentifier(dto.getServicePointIdentifier());
        entity.setSupplyStartDate(dto.getSupplyStartDate());
        entity.setVolume(dto.getVolume());
        return entity;
    }

    public static SupplyOrderDTO toDTO(SupplyOrder entity) {
        SupplyOrderDTO dto = new SupplyOrderDTO();
        dto.setClientNumber(entity.getClientNumber());
        dto.setClosingDay(entity.getClosingDay());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setServicePointIdentifier(entity.getServicePointIdentifier());
        dto.setSupplyStartDate(entity.getSupplyStartDate());
        dto.setVolume(entity.getVolume());
        return dto;
    }
}
