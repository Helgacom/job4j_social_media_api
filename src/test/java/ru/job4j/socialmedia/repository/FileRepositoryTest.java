package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.socialmedia.model.File;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileRepositoryTest {

    @Autowired
    private FileRepository fileRepository;

    @BeforeEach
    public void setUp() {
        fileRepository.deleteAll();
    }

    @AfterAll
    public void clearAll() {
        fileRepository.deleteAll();
    }

    @Test
    public void whenSaveFileThenFindById() {
        var file = new File();
        file.setName("testName");
        file.setPath("test/");
        fileRepository.save(file);
        var foundFile = fileRepository.findById(file.getId());
        assertThat(foundFile).isPresent();
        assertThat(foundFile.get().getName()).isEqualTo("testName");
    }

    @Test
    public void whenFindAllThenReturnAllFiles() {
        var file1 = new File();
        file1.setName("testName1");
        file1.setPath("test1/");
        var file2 = new File();
        file2.setName("testName2");
        file2.setPath("test2/");
        fileRepository.save(file1);
        fileRepository.save(file2);
        var files = fileRepository.findAll();
        assertThat(files).hasSize(2);
        assertThat(files).extracting(File::getName).contains("testName1", "testName2");
    }

    @Test
    public void whenDeleteFileThenFileNotFound() {
        var file = new File();
        file.setName("testName");
        file.setPath("test/");
        fileRepository.save(file);
        fileRepository.delete(file);
        var foundFile = fileRepository.findById(file.getId());
        assertThat(foundFile).isNotPresent();
        assertThat(fileRepository.findAll()).doesNotContain(file);
    }
}