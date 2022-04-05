package com.ai.sys.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
public class AlgoResult extends DateAudit{
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(generator = "algoresult_generator")
    @GenericGenerator(name="algoresult_generator", strategy = "increment")
    //TODO jun: 之前使用注释的注解生成的id不对，数据库里有5条数据，但是返回3
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "algo_id")
//    private Algo algo;
    private Long algoid;


    private Float accuracy;
    private Float callback;
}
