package com.trade.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
public class TestObject {

	@NotNull(message = "i is mandatory")
	private Integer i;

	@NotNull(message = "s is mandatory")
	private String s;

	@NotNull(message = "bd is mandatory")
	private BigDecimal bd;

	@NotNull(message = "t is mandatory")
	private LocalTime t;

	@NotNull(message = "d is mandatory")
	private LocalDate d;

	@NotNull(message = "dt is mandatory")
	private LocalDateTime dt;

	@NotNull(message = "u is mandatory")
	private UUID u;

	@NotNull(message = "l is mandatory")
	private List<String> l;

	@NotNull(message = "m is mandatory")
	private Map<String, String> m;

	public TestObject(Integer i, String s, BigDecimal bd,
					  LocalTime t, LocalDate d, LocalDateTime dt,
					  UUID u, List<String> l, Map<String, String> m) {
		this.i = i;
		this.s = s;
		this.bd = bd;
		this.t = t;
		this.d = d;
		this.dt = dt;
		this.u = u;
		this.l = l;
		this.m = m;
	}

	public boolean equals(Object object) {
		if (object instanceof TestObject) {
			TestObject to = (TestObject) object;
			return i.equals(to.i) &&
					s.equals(to.s) &&
					bd.compareTo(to.bd) == 0 &&
					t.equals(to.t) &&
					d.equals(to.d) &&
					dt.equals(to.dt) &&
					u.equals(to.u) &&
					l.equals(to.l) &&
					m.equals(to.m);
		}
		return false;
	}

	public String toString() {
		return new StringBuilder().
				append("i=").append(i).
				append(",s=").append(s).
				append(",bd=").append(bd).
				append(",t=").append(t).
				append(",d=").append(d).
				append(",dt=").append(dt).
				append(",u=").append(u).
				append(",l=").append(l).
				append(",m=").append(m).
				toString();
	}

	public Integer getI() {
		return i;
	}

	public String getS() {
		return s;
	}

	public LocalTime getT() {
		return t;
	}

	public LocalDate getD() {
		return d;
	}

	public LocalDateTime getDt() {
		return dt;
	}

	public List<String> getL() {
		return l;
	}

	public Map<String, String> getM() {
		return m;
	}

	public BigDecimal getBd() {
		return bd;
	}

	public UUID getU() {
		return u;
	}
}