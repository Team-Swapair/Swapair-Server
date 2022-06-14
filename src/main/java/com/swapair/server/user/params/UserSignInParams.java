package com.swapair.server.user.params;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignInParams {
    private String email;
    private String password;
}
