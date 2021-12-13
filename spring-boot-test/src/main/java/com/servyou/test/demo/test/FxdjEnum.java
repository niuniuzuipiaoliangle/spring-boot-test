package com.servyou.test.demo.test;

import lombok.Getter;

public enum FxdjEnum {
        FXDJ_GFX("1","高风险"),
        FXDJ_ZFX("2","中风险"),
        FXDJ_DFX("3","低风险");

        @Getter
        public String code;
        @Getter
        public String value;

        FxdjEnum(String code, String value) {
            this.code = code;
            this.value = value;
        }

        /**
         * 根据code获取去value
         *
         * @param code
         * @return
         */
        public static String getValue(String code) {
            for (FxdjEnum fxdj : FxdjEnum.values()) {
                if (fxdj.getCode().equalsIgnoreCase(code)) {
                    return fxdj.getValue();
                }
            }
            return null;
        }
}