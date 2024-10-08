package ru.job4j.socialmedia.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.job4j.socialmedia.model.File;
import ru.job4j.socialmedia.repository.FileRepository;

import java.util.List;

@Named("FileListMapperUtil")
@Component
@RequiredArgsConstructor
public class FileListMapperUtil {

    private final FileRepository fileRepository;

    @Named("findFilesByPostId")
    public List<File> findFilesByPostId(Long postId) {
        var fileList = fileRepository.findAll().stream()
                .filter(file -> file.getPost().getId().equals(postId))
                .toList();
        return fileList;
    }
}
