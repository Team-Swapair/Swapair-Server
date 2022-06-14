package com.swapair.server.user.params;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpParams {
    private String email;
    private String password;
    private String phoneNumber;
}
