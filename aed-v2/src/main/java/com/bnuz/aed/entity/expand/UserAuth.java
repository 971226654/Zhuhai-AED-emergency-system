package com.bnuz.aed.entity.expand;

import lombok.*;

/**
 * @author Leia Liang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAuth {

    private String userId;

    private String role;

    private String method;

}