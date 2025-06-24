package Asignatura.Asignatura.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema; 

@Entity
@Table(name="profesor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detalles de un profesor en el sistema académico.") // Anotación a nivel de clase
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del profesor.", example = "1")
    private Integer id;

    @Column(unique = true,nullable = true,length = 13)
    @Schema(description = "Rol Único Nacional (RUN) del profesor.", example = "12.345.678-9")
    private String run;

    @Column(nullable = false)
    @Schema(description = "Nombre de pila del profesor.", example = "Ana")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Apellido del profesor.", example = "García")
    private String apellido;

    @Column(nullable = false)
    @Schema(description = "Fecha de nacimiento del profesor en formato DD-MM-YYYY.", example = "15-05-1975")
    private String fechaNacimiento;

}