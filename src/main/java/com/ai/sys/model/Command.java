package com.ai.sys.model;

import com.ai.sys.model.entity.Algo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Command {
    Algo algo;
    String image;
    String fullCmd;
}
