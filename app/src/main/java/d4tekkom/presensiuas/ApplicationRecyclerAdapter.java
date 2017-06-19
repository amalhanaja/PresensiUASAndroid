package d4tekkom.presensiuas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by doy on 17/06/17.
 */

public abstract class ApplicationRecyclerAdapter<TipeData,ViewHolder extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<ViewHolder> {

    protected int mLayout;
    Class<ViewHolder> mViewHolderClass;
    Class<TipeData> mModelClass;
    List<TipeData> mData;

    public ApplicationRecyclerAdapter(int mLayout, Class<ViewHolder> mViewHolderClass, Class<TipeData> mModelClass, List<TipeData> mData){
        this.mLayout = mLayout;
        this.mViewHolderClass = mViewHolderClass;
        this.mModelClass = mModelClass;
        this.mData = mData;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(mLayout,parent,false);

        try{
            Constructor<ViewHolder> constructor = mViewHolderClass.getConstructor(View.class);
            return constructor.newInstance(view);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TipeData model = getItem(position);
        bindView(holder,model,position);
    }

    abstract protected void bindView(ViewHolder holder,TipeData model,int position);

    private TipeData getItem(int position){
        return mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
}
