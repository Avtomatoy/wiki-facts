package ru.avtomaton.wikifacts.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Set;

/**
 * @author Anton Akkuzin
 */
@Entity
@Table(name = "t_fact")
@Getter
@Setter
@NoArgsConstructor
public class Fact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String factText;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> categories;
    @ElementCollection
    private Set<String> links;
    private User author;
    @Temporal(TemporalType.DATE)
    private Date publicationDate;
    private Long rating;
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        UNVERIFIED,
        VERIFIED,
    }
}
