package com.ruoyi.hemerdinger.finance.domain.indicator;

public interface TimeIndicatorHandle<T> {
    T parsFromJson (String str);
}
