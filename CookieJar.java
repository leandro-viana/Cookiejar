import java.util.Random;

/**
 * Trabalho de Sistemas Operacionais - Problema da Lata do Biscoito
 * Elaborado por @leandrovianadc e @LucasMendes
*/
public class CookieJar {

static Object o = new Object();
static Object mother = new Object();
static Object children = new Object();

static int tinaCount = 0;
static int cookieCount = 25;
static Random random = new Random();

public static void main(String... args) {
new Thread(new Mother()).start();
new Thread(new Tina()).start();
new Thread(new Judy()).start();
}

private static void checkCookieCount() {
	if (cookieCount == 0) {
		synchronized (mother) {
			System.out.println("Sem cookies!!! MÃE!.");
			mother.notifyAll();
		}
		try {
			System.out
			.println("Estamos com fome mãe.");
			synchronized (children) {
				children.wait();
			}
		} catch (InterruptedException e) {
		}

	}

}

private static void randomWait() {
	int millisecs = random.nextInt(100);
	try {
		Thread.sleep(millisecs);
	} catch (InterruptedException e) {
	}
}

static class Tina implements Runnable {

@Override
public void run() {
	while (true) {
		randomWait();
		boolean empty = false;
		synchronized (o) {
			if (cookieCount == 0) {
				empty = true;
				synchronized (mother) {
					System.out
					.println("Sem bisoito mãe!");
					mother.notifyAll();
				}
			}
		}
		if (empty) {
			try {
				System.out
				.println("Estamos com fome mãe!");
				synchronized (children) {
					children.wait();
				}
			} catch (InterruptedException e) {
			}
		}
		synchronized (o) {

			cookieCount--;
			tinaCount++;
			System.out.println("TinaCount = " + tinaCount);
			o.notify();
		}
		System.out.println("Tina está comendo o biscoito.");
	}

	}

}

static class Judy implements Runnable {

@Override
public void run() {
	while (true) {
		randomWait();
		boolean empty = false;
		synchronized (o) {
			System.out.println("Judy está tentando pegar um biscoito.");

			while (tinaCount < 2) {
				System.out
				.println("Judy está eprando a Tina.");
				try {
					o.wait();
					System.out
					.println("Judy está agora pronta e esperando o tinaCount.");
				} catch (InterruptedException e) {
				}
			}
			if (cookieCount == 0) {
				empty = true;
				synchronized (mother) {
					System.out
					.println("Mãe ESTAMOS COM FOME!.");
					mother.notify();
				}
			}
		}

		if (empty) {
			try {
				System.out
				.println("FOME!!!.");
				synchronized (children) {
					children.wait();
				}
			} catch (InterruptedException e) {
			}
		}
		synchronized (o) {

		}
		System.out.println("Judy está comendo.");
		synchronized (o) {
			cookieCount--;
			tinaCount = 0;
			System.out.println("Judy agora resetou tinaCount.");
		}
	}

}

}

static class Mother implements Runnable {

@Override
public void run() {
	while (true) {
		synchronized (mother) {
			try {
				System.out
				.println("Mamãe está dormindo me acordem quando terminarem filhotas.");
				mother.wait();
			} catch (InterruptedException e) {
			}
		}
synchronized (o) {
cookieCount = 25;
	System.out
	.println("Mãe encheu a jarra.");
	}
	synchronized (children) {
		System.out
		.println("Pode pegar filha enchi a jarra.");
		children.notifyAll();
	}
}

}

}

} 