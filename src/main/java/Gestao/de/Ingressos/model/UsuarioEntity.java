package Gestao.de.Ingressos.model;

import Gestao.de.Ingressos.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TBL_USUARIO")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO")
    @SequenceGenerator(name = "SEQ_USUARIO", sequenceName = "SQ_USUARIO", allocationSize = 1)
    @Column(name = "COD_USUARIO")
    private Long id;

    @Column(name = "NOME_USUARIO")
    private String nomeUsuario;

    @Column(name = "NOME_APELIDO")
    private String nomeApelido;

    @Column(name = "NUM_CPF_CNPJ")
    private String cpf;

    @Column(name = "EMAIL_USUARIO")
    private String emailUsuario;

    @Column(name = "SENHA_USUARIO")
    private String senhaUsuario;

    @Column(name = "TXT_OBS")
    private String obs;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "ARQ_FOTO_PERFIL")
    private byte[] arqFotoPerfil;

    @Enumerated(EnumType.STRING)
    @Column(name = "PERFIL_USUARIO")
    private Role role;
}
