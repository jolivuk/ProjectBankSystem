package bank.app.model.entity;

import bank.app.model.enums.DocumentTypeName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yaml.snakeyaml.events.Event;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "document_type")
public class DocumentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="document_type_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type_name")
    private DocumentTypeName name;


    public void setName(DocumentTypeName name) {
        this.name = name;
    }
}
