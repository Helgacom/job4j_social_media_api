package ru.job4j.socialmedia.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.job4j.socialmedia.dto.RequestDto;
import ru.job4j.socialmedia.model.Request;

@Mapper(componentModel = "spring")
@Component
public interface RequestMapper {

    RequestDto getRequestDtoFromRequest(Request request);

    Request getRequestFromRequestDto(RequestDto requestDto);
}
