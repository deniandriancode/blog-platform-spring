package io.fam.bloggersite.entity;

import org.springframework.lang.NonNull;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NonNull
    @NotEmpty
    private String title;

    @NonNull
    @NotEmpty
    @Lob
    private String content; // use text data type from postgresql

}
