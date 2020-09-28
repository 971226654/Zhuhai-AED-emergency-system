package com.bnuz.aed.entity.expand;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Leia Liang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AedOutput implements Serializable {

    private static final long serialVersionUID = 1167929581558148785L;

    private Long equipmentId;

    private Long inspectorId;

    private String displayTime;

    private String productionTime;

    private String purchaseTime;

    private String factoryName;

    private String model;

    private int status;

    private byte[] appearance;

    private String longitude;

    private String latitude;

    private String country;

    private String city;

    private String address;

}
