package br.com.drivecore.application.authentication.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthenticationApplicationServiceConstants {

    public static final String WELCOME_SUBJECT = "Bem vindo(a) a TransTL!";

    public static String buildWelcomeTextWithTemporaryCredentials(String username, String temporaryCredentials) {
        return String.format(
                """
                        Para realizar acesso em nosso sistema, basta utilizar as instruções abaixo:
                        
                        usuário: %s
                        senha: %s
                        
                        Ao realizar o primeiro acesso, você precisará informar uma senha definitiva.
                        
                        """, username, temporaryCredentials
        );
    }

}
