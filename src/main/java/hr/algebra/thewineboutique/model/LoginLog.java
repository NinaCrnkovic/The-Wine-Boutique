package hr.algebra.thewineboutique.model;

@Entity
@Table(name = "login_logs")
public class LoginLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "login_time", nullable = false)
    private LocalDateTime loginTime;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    // Getters and setters
}

