package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class MyGdxGame implements ApplicationListener {

    World world = new World(new Vector2(0, -1), true);
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera camera;
    static final float BOX_STEP=1/60f;
    static final int BOX_VELOCITY_ITERATIONS=6;
    static final int BOX_POSITION_ITERATIONS=2;
    static final float WORLD_TO_BOX=0.01f;
    static final float BOX_WORLD_TO=100f;
    Body playerBody;
    BodyDef player;

    @Override
    public void create()
    {
        camera = new OrthographicCamera();
        camera.viewportHeight = Gdx.graphics.getHeight();
        camera.viewportWidth = Gdx.graphics.getWidth();
        camera.position.set(camera.viewportWidth * .5f, camera.viewportHeight * .5f, 0f);
        camera.update();


        //ground
        BodyDef ground = new BodyDef();
        ground.type = BodyDef.BodyType.StaticBody;
        ground.position.set(new Vector2(0,40));
        Body theGround = world.createBody(ground);
        PolygonShape gBox = new PolygonShape();
        gBox.setAsBox(Gdx.graphics.getWidth(), 5f);
        theGround.createFixture(gBox,0);

        //box
        player = new BodyDef();
        player.type = BodyDef.BodyType.DynamicBody;
        player.position.set(new Vector2(100,101));
        playerBody = world.createBody(player);
        PolygonShape playerBox =  new PolygonShape();
        playerBox.setAsBox(70,70);
        FixtureDef fixtureDef1 = new FixtureDef();
        fixtureDef1.shape = playerBox;
        fixtureDef1.density = 1f;
        fixtureDef1.friction = 1f;
        fixtureDef1.restitution = 1f;
        playerBody.createFixture(fixtureDef1);

        //box2
        BodyDef car = new BodyDef();
        car.type = BodyDef.BodyType.DynamicBody;
        car.position.set((new Vector2(Gdx.graphics.getWidth() - 100, 101)));
        Body carBody = world.createBody(car);
        PolygonShape carBox =  new PolygonShape();
        carBox.setAsBox(70,70);
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = playerBox;
        fixtureDef2.density = 1f;
        fixtureDef2.friction = 1f;
        fixtureDef2.restitution = 1f;
        carBody.createFixture(fixtureDef2);
        carBody.setLinearVelocity(-50f,0);





        debugRenderer = new Box2DDebugRenderer();
    }


    @Override
    public void render() {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isTouched())
        {
            playerBody.setLinearVelocity(0,200f);
        }

        debugRenderer.render(world, camera.combined);
        world.step(BOX_STEP, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS);
    }




    @Override
    public void dispose() {
        // dispose of all the native resources

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
