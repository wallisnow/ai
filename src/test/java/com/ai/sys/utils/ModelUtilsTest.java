package com.ai.sys.utils;

import com.ai.sys.model.entity.user.SysUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ModelUtilsTest {

    String uniqueField = "id";
    List<String> ignores = List.of("serialVersionUID");

    @Test
    void testCheckIfOnlyOneFieldHasValue() throws NoSuchFieldException, IllegalAccessException {
        //happy path
        SysUser user = SysUser.builder()
                .id(1L)
                .build();
        boolean onlyId = ModelUtils.checkIfOnlyOneFieldHasValue(SysUser.class, user, uniqueField, ignores);
        Assertions.assertTrue(onlyId);

        //has no id
        SysUser user1 = SysUser.builder()
                .build();
        boolean onlyId1 = ModelUtils.checkIfOnlyOneFieldHasValue(SysUser.class, user1, uniqueField, ignores);
        Assertions.assertFalse(onlyId1);

        //has other values
        SysUser user2 = SysUser.builder()
                .id(1L)
                .password("test")
                .build();

        boolean onlyId2 = ModelUtils.checkIfOnlyOneFieldHasValue(SysUser.class, user2, uniqueField, ignores);
        Assertions.assertFalse(onlyId2);
    }
}