package com.logistic.ai.adapter.in.internal.mapper;

import com.logistic.ai.adapter.in.internal.request.GetDeadLineRequest;
import com.logistic.ai.application.port.in.command.GetDeadLineCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AiServiceMapper {
  GetDeadLineCommand toCommand(GetDeadLineRequest request);
}


