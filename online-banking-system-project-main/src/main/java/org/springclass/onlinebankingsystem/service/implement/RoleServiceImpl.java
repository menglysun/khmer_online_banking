package org.springclass.onlinebankingsystem.service.implement;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springclass.onlinebankingsystem.exception.CustomException;
import org.springclass.onlinebankingsystem.repository.RoleRepository;
import org.springclass.onlinebankingsystem.repository.entity.Role;
import org.springclass.onlinebankingsystem.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    @Override
    @Transactional
    public Role add(Role account) {
        return repository.save(account);
    }

    @Override
    public Optional<List<Role>> findAll() {
        return repository.findAllByStatusTrueOrderByIdAsc();
    }

    @Override
    public Role findById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new CustomException(404, "Role id %d not found".formatted(id)));
    }

    @Override
    public Role update(Role role) {
        final var entity = findById(role.getId());
        entity.setName(role.getName());
        entity.setRole(role.getRole());
        return repository.save(entity);
    }

    @Override
    public Page<Role> findAll(Optional<String> query, int page, int size) {
        return repository.findAll((root, cq, cb) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();
            if (query.isPresent()) {
                var name = cb.like(cb.upper(root.get("name")), "%" + query.toString().toUpperCase() + "%");
                predicates.add(cb.or(name));
            }
            predicates.add(cb.isTrue(root.get("status")));
            return cb.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
    }
}
