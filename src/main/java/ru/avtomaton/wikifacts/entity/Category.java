package ru.avtomaton.wikifacts.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Anton Akkuzin
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_category")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public enum CategoryName {
        ANIMALS_NATURE_PLACES,
        ARCHITECTURE_ARTS,
        ENTERTAINMENT,
        HEALTH_FOOD_AND_DRINK,
        HISTORY_EVENTS,
        KNOWLEDGE_SCIENCE_TECHNOLOGY,
        PEOPLE_PERSONS_CHARACTERS,
        SOCIETY_CULTURE_HUMAN_BEHAVIOUR,
        SPORTS,
        TRANSPORT,
    }
}
