package com.example.repository;

import com.example.repository.packaze.PackageEntity;
import com.example.repository.packaze.PackageRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class PackageRepositoryTest {

    @Autowired
    private PackageRepository packageRepository;

    @Test
    public void test_save() {
        PackageEntity packageEntity = new PackageEntity();
        packageEntity.setPackageName("바디 챌린지 PT 12주");
        packageEntity.setPeriod(84);

        packageRepository.save(packageEntity);

        assertNotNull(packageEntity.getPackageSeq());
    }

    @Test
    public void test_findByCreatedAtAfter() { //특정 시점 이후에 생성된 것들 찾기
        LocalDateTime dateTime = LocalDateTime.now().minusMinutes(1); //지금 시점에서 1분 전

        PackageEntity entity0 = new PackageEntity();
        entity0.setPackageName("학생 전용 3개월");
        entity0.setPeriod(90);
        packageRepository.save(entity0);

        PackageEntity entity1 = new PackageEntity();
        entity1.setPackageName("학생 전용 6개월");
        entity1.setPeriod(180);
        packageRepository.save(entity1);

        final List<PackageEntity> packageEntities = packageRepository.findByCreatedAtAfter(dateTime,
                PageRequest.of(0, 1, Sort.by("packageSeq").descending())
        );

        assertEquals(1, packageEntities.size());
        assertEquals(entity1.getPackageSeq(), packageEntities.get(0).getPackageSeq());
    }

    @Test
    public void test_updateCountAndPeriod() {
        PackageEntity entity0 = new PackageEntity();
        entity0.setPackageName("바디프로필 이벤트 4개월");
        entity0.setPeriod(90);
        packageRepository.save(entity0);

        int updatedCount = packageRepository.updateCountAndPeriod(entity0.getPackageSeq(), 30, 120);

        final PackageEntity updatedPackageEntity = packageRepository.findById(entity0.getPackageSeq()).get();

        assertEquals(1, updatedCount);
        assertEquals(30, updatedPackageEntity.getCount());
        assertEquals(120, updatedPackageEntity.getPeriod());
    }

    @Test
    public void test_delete() {
        PackageEntity entity0 = new PackageEntity();
        entity0.setPackageName("제거할 이용권");
        entity0.setCount(1);
        PackageEntity newEntity = packageRepository.save(entity0);

        packageRepository.deleteById(newEntity.getPackageSeq());

        assertTrue(packageRepository.findById(newEntity.getPackageSeq()).isEmpty());
    }
}