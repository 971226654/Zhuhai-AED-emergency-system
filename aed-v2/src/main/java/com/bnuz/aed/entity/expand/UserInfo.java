package com.bnuz.aed.entity.expand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Leia Liang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private String userName;

    private String phoneNumber;

    private String idCard;

    private String email;

}
