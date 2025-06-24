package Asignatura.Asignatura.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel; // ¡Importante para HATEOAS!

@Entity
@Table(name="asignatura")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detalles de una asignatura en el sistema académico.") // Anotación a nivel de clase
public class Asignatura extends RepresentationModel<Asignatura> { // ¡Extiende RepresentationModel para HATEOAS!
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la asignatura.", example = "101") // Anotación a nivel de propiedad
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre completo de la asignatura.", example = "Matemáticas Discretas") // Anotación a nivel de propiedad
    private String nombreAsignatura;

    @ManyToOne
    @JoinColumn(name = "profesor_id", nullable = false)
    @Schema(description = "Profesor asignado para impartir esta asignatura.") // Anotación a nivel de propiedad
    private Profesor profesor;
}