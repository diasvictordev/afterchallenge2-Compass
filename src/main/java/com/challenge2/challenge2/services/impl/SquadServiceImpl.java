package com.challenge2.challenge2.services.impl;

import com.challenge2.challenge2.dto.SquadDTO;
import com.challenge2.challenge2.entities.Squad;
import com.challenge2.challenge2.entities.Student;
import com.challenge2.challenge2.exceptions.BadRequestException;
import com.challenge2.challenge2.repositories.SquadRepository;
import com.challenge2.challenge2.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SquadServiceImpl implements SquadService{



    private static SquadRepository squadRepository;
    private static StudentRepository studentRepository;


    public SquadServiceImpl(SquadRepository squadRepository, StudentRepository studentRepository) {
        SquadServiceImpl.studentRepository = studentRepository;
        SquadServiceImpl.squadRepository = squadRepository;
    }

    @Override
    public List<Squad> getAllSquads() throws BadRequestException {
        return squadRepository.findAll();
    }

    @Override
    public Optional<Squad> getSquadById(Long id) {
        Optional<Squad> squadOptional = squadRepository.findById(id);
        return squadOptional;
    }

    @Override
    public Squad saveSquad(Squad squad) {

        return squadRepository.save(squad);
    }


    /*
    public Squad createSquadWithStudents(SquadDTO squadDTO) {

        List<Long> studentIds = squadDTO.getStudents();
        Squad squad = new Squad();
        squad.setSquadName(squadDTO.getSquadName());

        for (Long studentId : studentIds) {
            Optional<Student> student = studentRepository.findById(studentId);

            if (!studentRepository.existsById(studentId)) {
                throw new IllegalArgumentException("O aluno com ID " + studentId + " não foi encontrado.");
            }
            if (student.get().getSquad() != null) {
                throw new IllegalArgumentException("O aluno já se encontra em um squad, remova-o do squad para adicioná-lo em outro");
            }

            squad.getStudents().add(student.get());
            studentRepository.findById(studentId).get().setSquad(squad);
        }
        return squadRepository.save(squad);
    }*/

    public Squad createSquadWithStudents(SquadDTO squadDTO) {
        List<Long> studentIds = squadDTO.getStudents();
        Squad squad = new Squad();
        squad.setSquadName(squadDTO.getSquadName());

        for (Long studentId : studentIds) {
            Optional<Student> studentOptional = studentRepository.findById(studentId);
            Student student = studentOptional.orElseThrow(() -> new IllegalArgumentException("O aluno com ID " + studentId + " não foi encontrado."));
            System.out.println(studentOptional.get().getId());

            if (student.getSquad() != null) {
                throw new IllegalArgumentException("O aluno já se encontra em um squad, remova-o do squad para adicioná-lo em outro");
            }

            squad.getStudents().add(student);
            student.setSquad(squad);
        }

        return squadRepository.save(squad);
    }

/*
    public Squad createSquadWithStudents(SquadDTO squadDTO) {

        List<Long> studentIds = squadDTO.getStudents();
        Squad squad = new Squad();
        squad.setSquadName(squadDTO.getSquadName());

        for (Long studentId : studentIds) {
            Optional<Student> studentOptional = studentRepository.findById(studentId);

            if (!studentOptional.isPresent()) {
                throw new IllegalArgumentException("O aluno com ID " + studentId + " não foi encontrado.");
            }

            Student student = studentOptional.get();

            if (student.getSquad() != null) {
                throw new IllegalArgumentException("O aluno já se encontra em um squad, remova-o do squad para adicioná-lo em outro");
            }

            squad.getStudents().add(student);
            //student.setSquad(squad);
        }

        return squadRepository.save(squad);
    }
*/
/*
    public void putSquadInStudents(Squad squad, SquadDTO squadDTO) {
        List <Long> studentIds = squadDTO.getStudents();
        for (Long studentId : studentIds) {
            Optional<Student> student = studentRepository.findById(studentId);
            Student student1 = student.get();
            student1.setSquad(squad);
            System.out.println(studentId);
            System.out.println(student1.toString());
            //studentRepository.save(student1);
        }
    }
*/

    @Override
    public void deleteSquad(Long id) {

        squadRepository.deleteById(id);
    }

}
