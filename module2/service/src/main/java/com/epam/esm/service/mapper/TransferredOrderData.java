package com.epam.esm.service.mapper;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TransferredOrderData {

    private User user;
    private GiftCertificate giftCertificate;
}
