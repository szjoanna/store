package io.asia.store.domain.dao;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product implements IdentifiedDataSerializable {
    @Id
    @GeneratedValue //    (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private boolean main;
    @ManyToMany
    private List<Product> listOfProducts;
    @Version
    private Long version;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @ManyToOne
    private Category category;
    private Double quantity;
    private String imageUrl;
    private boolean deleted;

    @Override
    public int getFactoryId() {
        return 1;
    }

    @Override
    public int getClassId() {
        return 1;
    }

    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
        objectDataOutput.writeLong(id);
        objectDataOutput.writeUTF( name );
        objectDataOutput.writeUTF( description );
        objectDataOutput.writeDouble( price );
        objectDataOutput.writeLong( version );
        objectDataOutput.writeDouble(quantity);
        objectDataOutput.writeBoolean(deleted);
        objectDataOutput.writeUTF(imageUrl);
    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {
        id = objectDataInput.readLong();
        name = objectDataInput.readUTF();
        description = objectDataInput.readUTF();
        price = objectDataInput.readDouble();
        version = objectDataInput.readLong();
        quantity = objectDataInput.readDouble();
        deleted = objectDataInput.readBoolean();
        imageUrl = objectDataInput.readUTF();
    }
}
