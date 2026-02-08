package Gestao.de.Ingressos.dto;

import Gestao.de.Ingressos.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private Role role;
}
