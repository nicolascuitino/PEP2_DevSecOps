package com.devsecops.app.controllers;

import com.devsecops.app.models.Task;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @GetMapping("/request")
    public String requestForm() {
        return "request";
    }

    @GetMapping("/reserva")
    public String reservaForm() {
        return "reserva";
    }

    @PostMapping("/submit-reserva")
    public String handleReserva(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String correo,
            @RequestParam String recurso,
            @RequestParam String fecha,
            @RequestParam String hora,
            @RequestParam int duracion,
            Model model) {

        // Simular el procesamiento de la reserva
        System.out.println("Reserva enviada:");
        System.out.println("Nombre: " + nombre);
        System.out.println("Apellido: " + apellido);
        System.out.println("Correo: " + correo);
        System.out.println("Recurso: " + recurso);
        System.out.println("Fecha: " + fecha);
        System.out.println("Hora: " + hora);
        System.out.println("Duración: " + duracion + " horas");

        // Agregar atributo de éxito para mostrar el mensaje en la vista
        model.addAttribute("success", true);

        // Redirigir de nuevo al formulario con un mensaje de confirmación
        return "reserva";
    }

    @PostMapping("/submit-request")
    public String handleRequest(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String rut,
            @RequestParam String correo,
            @RequestParam String solicitud,
            Model model) {

        // Simular el procesamiento de la solicitud
        System.out.println("Solicitud enviada:");
        System.out.println("Nombre: " + nombre);
        System.out.println("Apellido: " + apellido);
        System.out.println("RUT: " + rut);
        System.out.println("Correo: " + correo);
        System.out.println("Solicitud: " + solicitud);

        // Agregar atributo de éxito para mostrar el mensaje en la vista
        model.addAttribute("success", true);

        // Redirigir de nuevo al formulario con un mensaje de confirmación
        return "request";
    }


    @GetMapping("/profile")
    public String userProfile(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("role", authentication.getAuthorities().toString());
        return "profile";
    }

    @GetMapping("/todo")
    public String todoList(HttpSession session, Model model) {
        List<Task> tasks = (List<Task>) session.getAttribute("tasks");
        if (tasks == null) {
            tasks = new ArrayList<>();
            session.setAttribute("tasks", tasks);
        }
        model.addAttribute("tasks", tasks);
        return "todo";
    }

    @PostMapping("/todo/add")
    public String addTask(HttpSession session, @RequestParam String task) {
        List<Task> tasks = (List<Task>) session.getAttribute("tasks");
        tasks.add(new Task(task, false));
        return "redirect:/todo";
    }

    @GetMapping("/todo/delete/{index}")
    public String deleteTask(HttpSession session, @PathVariable int index) {
        List<Task> tasks = (List<Task>) session.getAttribute("tasks");
        if (tasks != null && tasks.size() > index) {
            tasks.remove(index);
        }
        return "redirect:/todo";
    }

    @GetMapping("/todo/toggle/{index}")
    public String toggleTask(HttpSession session, @PathVariable int index) {
        List<Task> tasks = (List<Task>) session.getAttribute("tasks");
        if (tasks != null && tasks.size() > index) {
            Task task = tasks.get(index);
            task.setCompleted(!task.isCompleted());
        }
        return "redirect:/todo";
    }

    @GetMapping("/todo/edit/{index}")
    public String editTaskForm(HttpSession session, @PathVariable int index, Model model) {
        List<Task> tasks = (List<Task>) session.getAttribute("tasks");
        if (tasks != null && tasks.size() > index) {
            Task task = tasks.get(index);
            model.addAttribute("task", task);
            model.addAttribute("index", index);
            return "editTask";
        }
        return "redirect:/todo";
    }

    @PostMapping("/todo/edit/{index}")
    public String editTask(HttpSession session, @PathVariable int index, @RequestParam String description) {
        List<Task> tasks = (List<Task>) session.getAttribute("tasks");
        if (tasks != null && tasks.size() > index) {
            Task task = tasks.get(index);
            task.setDescription(description);
        }
        return "redirect:/todo";
    }



}
