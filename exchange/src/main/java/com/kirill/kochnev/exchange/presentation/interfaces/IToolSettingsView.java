package com.kirill.kochnev.exchange.presentation.interfaces;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.exchange.data.enums.ToolType;
import com.kirill.kochnev.exchange.domain.models.TickUI;

import java.util.List;

/**
 * Created by Kirill Kochnev on 28.07.17.
 */

@StateStrategyType(SkipStrategy.class)
public interface IToolSettingsView extends IMessageView {

    void droptToolType(ToolType type);

    void invalidateToolTypeList(List<ToolType> toolTypes);

}
