package br.com.drivecore.core.generics.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
public class IdReferenceGenericDTO {

    private UUID id;

}
