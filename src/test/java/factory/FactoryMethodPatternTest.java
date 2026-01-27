package factory;

import model.DarkMage;
import model.Demon;
import model.Enemy;
import model.Goblin;
import model.Hero;
import model.HeroType;
import model.Orc;
import model.Skeleton;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import strategy.EnemyAI;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Factory Method pattern implementation.
 * Verifies structure and behaviour match Refactoring.Guru's description:
 * https://refactoring.guru/design-patterns/factory-method
 *
 * Structure: Product (interface), Concrete Products, Creator (abstract), Concrete Creators.
 * Rules: factory method return type = Product; all products implement Product;
 * concrete creators override factory method and return different product types.
 */
@DisplayName("Factory Method Pattern")
class FactoryMethodPatternTest {

    // -------------------------------------------------------------------------
    // 1. PRODUCT – "declares the interface common to all objects produced"
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("1. Product (Enemy interface)")
    class ProductTests {

        @Test
        @DisplayName("Enemy is an interface")
        void enemyIsInterface() {
            assertTrue(Enemy.class.isInterface());
        }

        @Test
        @DisplayName("All concrete products implement Enemy")
        void concreteProductsImplementEnemy() {
            assertTrue(Enemy.class.isAssignableFrom(Goblin.class));
            assertTrue(Enemy.class.isAssignableFrom(Skeleton.class));
            assertTrue(Enemy.class.isAssignableFrom(Orc.class));
            assertTrue(Enemy.class.isAssignableFrom(DarkMage.class));
            assertTrue(Enemy.class.isAssignableFrom(Demon.class));
        }

        @Test
        @DisplayName("Products can be used via Enemy reference only")
        void productsUsableViaInterfaceOnly() {
            Enemy enemy = new GoblinCreator().createEnemy(1);
            assertEquals("Goblin", enemy.getName());
            assertTrue(enemy.getHealth() > 0);
            assertTrue(enemy.getMaxHealth() > 0);
            assertTrue(enemy.isAlive());
            enemy.takeDamage(1000);
            assertFalse(enemy.isAlive());
        }
    }

    // -------------------------------------------------------------------------
    // 2. CONCRETE PRODUCTS – "different implementations of the product interface"
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("2. Concrete Products")
    class ConcreteProductTests {

        @Test
        @DisplayName("GoblinCreator creates Goblin")
        void goblinCreatorCreatesGoblin() {
            Enemy product = new GoblinCreator().createEnemy(1);
            assertInstanceOf(Goblin.class, product);
            assertEquals("Goblin", product.getName());
        }

        @Test
        @DisplayName("SkeletonCreator creates Skeleton")
        void skeletonCreatorCreatesSkeleton() {
            Enemy product = new SkeletonCreator().createEnemy(1);
            assertInstanceOf(Skeleton.class, product);
            assertEquals("Skeleton", product.getName());
        }

        @Test
        @DisplayName("OrcCreator creates Orc")
        void orcCreatorCreatesOrc() {
            Enemy product = new OrcCreator().createEnemy(1);
            assertInstanceOf(Orc.class, product);
            assertEquals("Orc", product.getName());
        }

        @Test
        @DisplayName("DarkMageCreator creates DarkMage")
        void darkMageCreatorCreatesDarkMage() {
            Enemy product = new DarkMageCreator().createEnemy(1);
            assertInstanceOf(DarkMage.class, product);
            assertEquals("Dark Mage", product.getName());
        }

        @Test
        @DisplayName("DemonCreator creates Demon")
        void demonCreatorCreatesDemon() {
            Enemy product = new DemonCreator().createEnemy(1);
            assertInstanceOf(Demon.class, product);
            assertEquals("Demon", product.getName());
        }

        @Test
        @DisplayName("Each creator returns a different product type")
        void creatorsReturnDifferentProductTypes() {
            Enemy g = new GoblinCreator().createEnemy(1);
            Enemy s = new SkeletonCreator().createEnemy(1);
            Enemy o = new OrcCreator().createEnemy(1);
            Enemy d = new DarkMageCreator().createEnemy(1);
            Enemy m = new DemonCreator().createEnemy(1);
            assertNotSame(g.getClass(), s.getClass());
            assertNotSame(s.getClass(), o.getClass());
            assertNotSame(o.getClass(), d.getClass());
            assertNotSame(d.getClass(), m.getClass());
        }
    }

    // -------------------------------------------------------------------------
    // 3. CREATOR – "declares the factory method that returns new product objects"
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("3. Creator")
    class CreatorTests {

        @Test
        @DisplayName("EnemyCreator is abstract")
        void creatorIsAbstract() {
            assertTrue(Modifier.isAbstract(EnemyCreator.class.getModifiers()));
        }

        @Test
        @DisplayName("Factory method return type is Enemy (Product interface)")
        void factoryMethodReturnTypeIsProduct() {
            Enemy product = new GoblinCreator().createEnemy(1);
            assertNotNull(product);
            assertTrue(product instanceof Enemy);
        }

        @Test
        @DisplayName("All concrete creators extend EnemyCreator")
        void concreteCreatorsExtendCreator() {
            assertTrue(EnemyCreator.class.isAssignableFrom(GoblinCreator.class));
            assertTrue(EnemyCreator.class.isAssignableFrom(SkeletonCreator.class));
            assertTrue(EnemyCreator.class.isAssignableFrom(OrcCreator.class));
            assertTrue(EnemyCreator.class.isAssignableFrom(DarkMageCreator.class));
            assertTrue(EnemyCreator.class.isAssignableFrom(DemonCreator.class));
        }
    }

    // -------------------------------------------------------------------------
    // 4. "All products must follow the same interface" – use as abstract type
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("4. Products used via abstract type (Enemy)")
    class UseViaInterfaceTests {

        @Test
        @DisplayName("takeDamage, heal, isAlive work when using Enemy reference")
        void damageHealAliveViaInterface() {
            Enemy enemy = new SkeletonCreator().createEnemy(1);
            int max = enemy.getMaxHealth();
            assertTrue(enemy.isAlive());
            int healthBefore = enemy.getHealth();
            enemy.takeDamage(10);
            assertTrue(enemy.getHealth() < healthBefore);
            assertTrue(enemy.isAlive());
            int healthAfterDamage = enemy.getHealth();
            enemy.heal(5);
            assertTrue(enemy.getHealth() > healthAfterDamage);
            enemy.takeDamage(max + 100);
            assertFalse(enemy.isAlive());
            assertEquals(0, enemy.getHealth());
        }

        @Test
        @DisplayName("decideAction works for any Enemy without knowing concrete type")
        void decideActionViaInterface() {
            for (EnemyCreator creator : new EnemyCreator[]{
                    new GoblinCreator(), new SkeletonCreator(), new OrcCreator(),
                    new DarkMageCreator(), new DemonCreator()
            }) {
                Enemy enemy = creator.createEnemy(1);
                EnemyAI.Action action = enemy.decideAction(50, 100);
                assertNotNull(action);
                assertTrue(java.util.Arrays.asList(EnemyAI.Action.values()).contains(action));
            }
        }

        @Test
        @DisplayName("Client code can use any product as Enemy without type checks")
        void noConcreteTypeChecksRequired() {
            Enemy[] products = {
                    new GoblinCreator().createEnemy(1),
                    new SkeletonCreator().createEnemy(1),
                    new OrcCreator().createEnemy(1),
                    new DarkMageCreator().createEnemy(1),
                    new DemonCreator().createEnemy(1)
            };
            for (Enemy e : products) {
                assertFalse(e.getName().isEmpty());
                assertTrue(e.getHealth() > 0);
                assertTrue(e.getMaxHealth() > 0);
                assertTrue(e.getAttack() >= 0);
                assertTrue(e.getDefense() >= 0);
                assertTrue(e.getGoldReward() >= 0);
                assertNotNull(e.getStrategy());
                assertTrue(e.isAlive());
            }
        }
    }

    // -------------------------------------------------------------------------
    // 5. Behaviour: correct names, strategies, stats scale with level
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("5. Behaviour and configuration")
    class BehaviourTests {

        @Test
        @DisplayName("Goblin uses Defensive AI")
        void goblinHasDefensiveAI() {
            Enemy e = new GoblinCreator().createEnemy(1);
            assertEquals("Defensive", e.getStrategy().getStrategyName());
        }

        @Test
        @DisplayName("Skeleton and Demon use Aggressive AI")
        void skeletonAndDemonHaveAggressiveAI() {
            assertEquals("Aggressive", new SkeletonCreator().createEnemy(1).getStrategy().getStrategyName());
            assertEquals("Aggressive", new DemonCreator().createEnemy(1).getStrategy().getStrategyName());
        }

        @Test
        @DisplayName("Orc and DarkMage use Smart AI")
        void orcAndDarkMageHaveSmartAI() {
            assertEquals("Smart", new OrcCreator().createEnemy(1).getStrategy().getStrategyName());
            assertEquals("Smart", new DarkMageCreator().createEnemy(1).getStrategy().getStrategyName());
        }

        @Test
        @DisplayName("Stats scale with level (higher level => higher stats)")
        void statsScaleWithLevel() {
            Enemy low = new GoblinCreator().createEnemy(1);
            Enemy high = new GoblinCreator().createEnemy(5);
            assertTrue(high.getMaxHealth() >= low.getMaxHealth());
            assertTrue(high.getAttack() >= low.getAttack());
            assertTrue(high.getDefense() >= low.getDefense());
            assertTrue(high.getGoldReward() >= low.getGoldReward());
        }

        @Test
        @DisplayName("Different product types have different base stats (e.g. Demon vs Goblin)")
        void differentProductsDifferentStats() {
            Enemy goblin = new GoblinCreator().createEnemy(1);
            Enemy demon = new DemonCreator().createEnemy(1);
            assertTrue(demon.getMaxHealth() > goblin.getMaxHealth() ||
                    demon.getAttack() > goblin.getAttack() ||
                    demon.getGoldReward() > goblin.getGoldReward());
        }
    }

    // -------------------------------------------------------------------------
    // 6. EnemyFactory (uses the pattern) – createEnemy, unknown type, etc.
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("6. EnemyFactory (uses Factory Method)")
    class EnemyFactoryTests {

        @Test
        @DisplayName("createEnemy returns correct product per EnemyType")
        void createEnemyReturnsCorrectProductPerType() {
            assertInstanceOf(Goblin.class,
                    EnemyFactory.createEnemy(EnemyFactory.EnemyType.GOBLIN, 1));
            assertInstanceOf(Skeleton.class,
                    EnemyFactory.createEnemy(EnemyFactory.EnemyType.SKELETON, 1));
            assertInstanceOf(Orc.class,
                    EnemyFactory.createEnemy(EnemyFactory.EnemyType.ORC, 1));
            assertInstanceOf(DarkMage.class,
                    EnemyFactory.createEnemy(EnemyFactory.EnemyType.DARK_MAGE, 1));
            assertInstanceOf(Demon.class,
                    EnemyFactory.createEnemy(EnemyFactory.EnemyType.DEMON, 1));
        }

        @Test
        @DisplayName("createEnemy returns Enemy (product interface)")
        void createEnemyReturnsEnemy() {
            Enemy e = EnemyFactory.createEnemy(EnemyFactory.EnemyType.GOBLIN, 1);
            assertNotNull(e);
            assertTrue(e instanceof Enemy);
        }

        @Test
        @DisplayName("createRandomEnemy returns valid Enemy")
        void createRandomEnemyReturnsEnemy() {
            Enemy e = EnemyFactory.createRandomEnemy(1);
            assertNotNull(e);
            assertFalse(e.getName().isEmpty());
            assertTrue(e.isAlive());
        }

        @Test
        @DisplayName("createEnemyByIndex returns valid Enemy")
        void createEnemyByIndexReturnsEnemy() {
            for (int i = 0; i < 10; i++) {
                Enemy e = EnemyFactory.createEnemyByIndex(i, 1);
                assertNotNull(e);
                assertFalse(e.getName().isEmpty());
            }
        }

        @Test
        @DisplayName("getAllEnemyTypes returns all registered types")
        void getAllEnemyTypes() {
            assertEquals(5, EnemyFactory.getAllEnemyTypes().length);
        }

        @Test
        @DisplayName("registerCreator allows overriding creator (extensibility)")
        void registerCreatorAllowsOverride() {
            EnemyCreator original = EnemyFactory.getCreator(EnemyFactory.EnemyType.GOBLIN);
            try {
                EnemyFactory.registerCreator(EnemyFactory.EnemyType.GOBLIN, new GoblinCreator());
                Enemy e = EnemyFactory.createEnemy(EnemyFactory.EnemyType.GOBLIN, 1);
                assertInstanceOf(Goblin.class, e);
            } finally {
                EnemyFactory.registerCreator(EnemyFactory.EnemyType.GOBLIN, original);
            }
        }
    }

    // -------------------------------------------------------------------------
    // 7. applyRandomEffect (Enemy interface) – no exception when called
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("7. Interface methods (applyRandomEffect)")
    class InterfaceMethodTests {

        @Test
        @DisplayName("applyRandomEffect can be called via Enemy reference without throwing")
        void applyRandomEffectViaInterface() {
            Hero hero = new Hero("Test", HeroType.KNIGHT);
            Enemy enemy = new GoblinCreator().createEnemy(1);
            assertDoesNotThrow(() -> enemy.applyRandomEffect(hero));
        }
    }
}
