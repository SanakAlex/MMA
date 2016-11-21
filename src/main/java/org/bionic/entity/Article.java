package org.bionic.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long articleId;
    private String articleName;
    private String context;
    // resource content type
    @ManyToOne
    @JoinColumn(name="resourceGroupTypeId")
    private ResourceGroupType resourceGroupType;
    // rang of resource, it means user of which rang can see it
    @ManyToOne
    @JoinColumn(name="rangId")
    private Rang rang;
    // @OneToMany(mappedBy = "rang")
  //  @LazyCollection(LazyCollectionOption.FALSE)
   // private Collection<User> userCollection;
}