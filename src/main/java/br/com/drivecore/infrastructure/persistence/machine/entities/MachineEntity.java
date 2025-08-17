package br.com.drivecore.infrastructure.persistence.machine.entities;

import br.com.drivecore.core.generics.infrastructure.persistence.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

//@Entity
//@Table(name = "machines")
@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MachineEntity extends BaseEntity {


}
